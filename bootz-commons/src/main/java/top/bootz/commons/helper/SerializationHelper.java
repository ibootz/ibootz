package top.bootz.commons.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.exception.SerializationException;

@Slf4j
public final class SerializationHelper {

    private SerializationHelper() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(Serializable object) {
        log.trace("Starting clone through serialization");
        if (object == null) {
            return null;
        }
        return (T) deserialize(serialize(object), object.getClass().getClassLoader());
    }

    public static void serialize(Serializable obj, OutputStream outputStream) throws SerializationException {
        if (outputStream == null) {
            throw new IllegalArgumentException("The OutputStream must not be null");
        }

        if (log.isTraceEnabled()) {
            log.trace("Starting serialization of object [{0}]", obj);
        }

        try (ObjectOutputStream out = new ObjectOutputStream(outputStream);) {
            out.writeObject(obj);
        } catch (IOException ex) {
            throw new SerializationException("could not serialize", ex);
        }
    }

    public static byte[] serialize(Serializable obj) throws SerializationException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        serialize(obj, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static <T> T deserialize(InputStream inputStream) throws SerializationException {
        return doDeserialize(inputStream, defaultClassLoader(), getClassLoader(), null);
    }

    public static ClassLoader defaultClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static ClassLoader getClassLoader() {
        return SerializationHelper.class.getClassLoader();
    }

    public static <T> T deserialize(InputStream inputStream, ClassLoader loader) throws SerializationException {
        return doDeserialize(inputStream, loader, defaultClassLoader(), getClassLoader());
    }

    @SuppressWarnings("unchecked")
    public static <T> T doDeserialize(InputStream inputStream, ClassLoader loader, ClassLoader fallbackLoader1,
            ClassLoader fallbackLoader2) throws SerializationException {
        if (inputStream == null) {
            throw new IllegalArgumentException("The InputStream must not be null");
        }

        log.trace("Starting deserialization of object");

        try (CustomObjectInputStream in = new CustomObjectInputStream(inputStream, loader, fallbackLoader1,
                fallbackLoader2);) {
            return (T) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new SerializationException("could not deserialize", e);
        }

    }

    public static <T> T deserialize(byte[] objectData) throws SerializationException {
        return doDeserialize(wrap(objectData), defaultClassLoader(), getClassLoader(), null);
    }

    private static InputStream wrap(byte[] objectData) {
        if (objectData == null) {
            throw new IllegalArgumentException("The byte[] must not be null");
        }
        return new ByteArrayInputStream(objectData);
    }

    public static Object deserialize(byte[] objectData, ClassLoader loader) throws SerializationException {
        return doDeserialize(wrap(objectData), loader, defaultClassLoader(), getClassLoader());
    }

    private static final class CustomObjectInputStream extends ObjectInputStream {
        private final ClassLoader loader1;
        private final ClassLoader loader2;
        private final ClassLoader loader3;

        private CustomObjectInputStream(InputStream in, ClassLoader loader1, ClassLoader loader2, ClassLoader loader3)
                throws IOException {
            super(in);
            this.loader1 = loader1;
            this.loader2 = loader2;
            this.loader3 = loader3;
        }

        @Override
        protected Class<?> resolveClass(ObjectStreamClass v) throws IOException, ClassNotFoundException {
            final String className = v.getName();
            log.trace("Attempting to locate class [{0}]", className);

            try {
                return Class.forName(className, false, loader1);
            } catch (ClassNotFoundException e) {
                log.trace("Unable to locate class using given classloader");
            }

            if (different(loader1, loader2)) {
                try {
                    return Class.forName(className, false, loader2);
                } catch (ClassNotFoundException e) {
                    log.trace("Unable to locate class using given classloader");
                }
            }

            if (different(loader1, loader3) && different(loader2, loader3)) {
                try {
                    return Class.forName(className, false, loader3);
                } catch (ClassNotFoundException e) {
                    log.trace("Unable to locate class using given classloader");
                }
            }

            return super.resolveClass(v);
        }

        private boolean different(ClassLoader one, ClassLoader other) {
            if (one == null) {
                return other != null;
            }
            return !one.equals(other);
        }
    }
}
