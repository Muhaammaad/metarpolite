package com.muhaammaad.metarpolite.model.type;

public enum RequestType {
    Retrieve("retrieve");

    private final String text;

    /**
     * @param text value to be assigned
     */
    RequestType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
