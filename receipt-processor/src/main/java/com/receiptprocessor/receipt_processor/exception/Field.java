package com.receiptprocessor.receipt_processor.exception;

public  class Field {
        String name;
        String errorMsg;

        public Field(String name, String errorMsg) {
            this.name = name;
            this.errorMsg = errorMsg;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }