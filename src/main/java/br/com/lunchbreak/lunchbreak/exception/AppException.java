package br.com.lunchbreak.lunchbreak.exception;

public class AppException extends Exception {

    public static RuntimeException throwException(EntityType entityType, ExceptionType exceptionType, String... args) {
        String messageTemplate = getMessageTemplate(entityType, exceptionType);
        return throwException(exceptionType, messageTemplate, args);
    }

    public static RuntimeException throwException(EntityType entityType, ExceptionType exceptionType) {
        String messageTemplate = getMessageTemplate(entityType, exceptionType);
        return throwException(exceptionType, messageTemplate);
    }

    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }

    public static class DuplicateEntityException extends RuntimeException {
        public DuplicateEntityException(String message) {
            super(message);
        }
    }

    public static class NotNullException extends RuntimeException {
        public NotNullException(String message) {
            super(message);
        }
    }

    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }

    public static class DataNotFoundException extends RuntimeException {
        public DataNotFoundException(String message) {
            super(message);
        }
    }
    
    private static RuntimeException throwException(ExceptionType exceptionType, String messageTemplate, String... args) {
        if (ExceptionType.NOT_FOUND.equals(exceptionType)) {
            return new EntityNotFoundException(messageTemplate);
        } else if (ExceptionType.DUPLICATE.equals(exceptionType)) {
            return new DuplicateEntityException(messageTemplate);
        } else if (ExceptionType.VALIDATION_ERROR.equals(exceptionType)) {
            return new ValidationException(messageTemplate);
        }else if (ExceptionType.NOT_NULL.equals(exceptionType)) {
            return new NotNullException(messageTemplate);
        }else if (ExceptionType.DATA_NOT_FOUND.equals(exceptionType)) {
            return new DataNotFoundException(messageTemplate);
        }
        return new RuntimeException();
    }

    private static String getMessageTemplate(EntityType entityType, ExceptionType exceptionType) {
        return entityType.name().concat(".").concat(exceptionType.getName()).concat(".").concat(exceptionType.getDescription()).toLowerCase();
    }

}
