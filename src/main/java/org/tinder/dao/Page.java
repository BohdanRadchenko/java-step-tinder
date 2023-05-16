package org.tinder.dao;

record Page(int number) {
    private static final int defaultPageSize = 20;
    public String toSQL(){
        return String.format("""
                OFFSET %d
                LIMIT %d
                """,
                (number-1)*defaultPageSize,
                defaultPageSize);
    }
}
