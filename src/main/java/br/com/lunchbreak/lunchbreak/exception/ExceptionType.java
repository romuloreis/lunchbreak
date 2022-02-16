package br.com.lunchbreak.lunchbreak.exception;

public enum ExceptionType {
        NOT_FOUND("Not Found", "This item could not be found!"),
        DUPLICATE("Duplicate Item", "This item aleady exists!"),
        APP_ERROR("Application Failed", "Application failed!"),
        DATA_NOT_FOUND("Data Not Found", "This data could not be found!"),
        VALIDATION_ERROR("Validation", "This request is not valid!"),
        NOT_NULL("Not Null", "Parameter could not be null");

        private String name;
        private String description;

        ExceptionType(String name, String description){
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String toString(){
            return "Exception Type: "+this.getName().concat("description: "+this.getDescription());
        }
}
