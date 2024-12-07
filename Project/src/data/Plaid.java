package data;

import com.plaid.client.PlaidClient;
import com.plaid.client.request.*;
import com.plaid.client.response.*;
import com.plaid.client.response.Account;
import retrofit2.Response;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Plaid {

    static String accessToken = "REDACTED";


    public static List<com.plaid.client.response.Account> getPlaidAccounts() throws IOException {
        PlaidClient plaidClient = PlaidClient.newBuilder()
                .clientIdAndSecret("REDACTED", "REDACTED")
                .sandboxBaseUrl()
                .build();
        // get accounts
        Response<AccountsGetResponse> response =
                plaidClient
                        .service()
                        .accountsGet(new AccountsGetRequest(accessToken))
                        .execute();
        List<Account> accounts = response.body().getAccounts();
        return accounts;
    }

    public static List<TransactionsGetResponse.Transaction> getPlaidTransactions() throws IOException, ParseException {
        PlaidClient plaidClient = PlaidClient.newBuilder()
                .clientIdAndSecret("REDACTED", "REDACTED")
                .sandboxBaseUrl()
                .build();
        // get transactions
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = simpleDateFormat.parse("2019-01-01");
        Date endDate = simpleDateFormat.parse("2021-02-01");
        Response<TransactionsGetResponse> response = plaidClient.service().transactionsGet(
                new TransactionsGetRequest(
                        accessToken,
                        startDate,
                        endDate))
                .execute();
        for (TransactionsGetResponse.Transaction txn : response.body().getTransactions());
        List<TransactionsGetResponse.Transaction> transactions = response.body().getTransactions();
        return transactions;
    }

    public static List<LiabilitiesGetResponse.CreditCardLiability> getPlaidLiabilities() throws IOException, ParseException {
        PlaidClient plaidClient = PlaidClient.newBuilder()
                .clientIdAndSecret("REDACTED", "REDACTED")
                .sandboxBaseUrl()
                .build();
        // get liabilities
        Response<LiabilitiesGetResponse> response =
                plaidClient.service().liabilitiesGet(new LiabilitiesGetRequest(accessToken)).execute();
        LiabilitiesGetResponse.Liabilities liabilities = response.body().getLiabilities();
        List<LiabilitiesGetResponse.CreditCardLiability> liabilitiesStreams = liabilities.getCredit();
        return liabilitiesStreams;
    }

}
