package com.muhaammaad.metarpolite.model.type;

public enum FormatType {
    XML("xml");

    private final String text;

    /**
     * @param text value to be assigned
     */
    FormatType(final String text) {
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
