package com.muhaammaad.metarpolite.model.type;

public enum DataSourceType {
    Metars("metars"),
    Stations("stations");

    private final String text;

    /**
     * @param text value to be assigned
     */
    DataSourceType(final String text) {
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
