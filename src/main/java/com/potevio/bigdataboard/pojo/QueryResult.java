package com.potevio.bigdataboard.pojo;

import lombok.Data;

import java.math.BigInteger;

@Data
public class QueryResult {
    private byte proTitleId;
    private String gender;
    private BigInteger num;
}
