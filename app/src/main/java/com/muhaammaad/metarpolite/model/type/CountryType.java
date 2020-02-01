package com.muhaammaad.metarpolite.model.type;

public enum CountryType {
    GERMANY("DE");

    private final String text;

    /**
     * @param text value to be assigned
     */
    CountryType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }

    public String toStringWtihTilda() {
        return "~".concat(text);
    }
}
