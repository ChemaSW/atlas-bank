package com.atlas.bank.atlas_bank.transaction.exception;

public class AccountNotActiveException extends RuntimeException {
  public AccountNotActiveException(String message) {
    super(message);
  }
}
