package com.infinitus.bms_oa.wms_store.task;

//import okhttp3.*;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZoneOffset.UTC;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;
import static org.apache.commons.codec.digest.HmacAlgorithms.HMAC_SHA_256;

/**
 * Hmac 请求授权例子
 */
@SuppressWarnings({"ConstantConditions", "DuplicatedCode"})
public class HmacSampleRestTest {

  // 消费者名称与密钥
  private static final String CUSTOMER_USERNAME = "product";
  private static final String CUSTOMER_SECRET = "HDY5ZKTISETT9MRCOGQ5HIC2OIZCP2SL";

  private static final String BASE_URL = "https://gapi-dev.infinitus.com.cn";

  //private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

  private static final DateTimeFormatter RFC_7231_FORMATTER = DateTimeFormatter
      .ofPattern("EEE, dd MMM yyyy HH:mm:ss O").withLocale(Locale.ENGLISH);

  public static void main(String[] args) throws Exception {

    getSkus();

    getSkuPrices();

    searchSkusByName();

    getCommodities();

    getCommoditie();
  }


  private static void getSkus() throws NoSuchAlgorithmException {
//    String uri = "/product-api/skus?nos=18170-06,xxx&page={page}&size={size}";
    String uri = "/product-api/skus";
    // 发起请求
    getExample(uri);
  }

  private static void getSkuPrices() throws NoSuchAlgorithmException {
//    String uri = "/product-api/skus/{skuNo}/prices";
    String uri = "/product-api/skus/18465-28/prices";
    // 发起请求
    getExample(uri);
  }

  private static void searchSkusByName() throws NoSuchAlgorithmException, UnsupportedEncodingException {
    String keyword = URLEncoder.encode("养固健牌雪花酥", "UTF-8");
    String uri = "/product-api/skus/search/name?keyword=" + keyword;
    // 发起请求
    getExample(uri);
  }

  private static void getCommodities() throws NoSuchAlgorithmException {
//    String uri = "/product-api/commodities?nos=18170-06,xxx&page={page}&size={size}";";
    String uri = "/product-api/commodities";
    // 发起请求
    getExample(uri);
  }

  private static void getCommoditie() throws NoSuchAlgorithmException {
//    String uri = "/product-api/commodities/{commodityNo}";
    String uri = "/product-api/commodities/18465-28";
    // 发起请求
    getExample(uri);
  }



  private static void getExample(String uri) throws NoSuchAlgorithmException {
    System.out.println("------------- GET --------------");
    String method = "GET";
    // 时间
    String date = RFC_7231_FORMATTER.format(ZonedDateTime.now(UTC));
//    String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
// 时间 + 请求类型 + 请求uri 的加密
    String signature =
        encodeBase64String(
            new HmacUtils(HmacAlgorithms.HMAC_SHA_256, CUSTOMER_SECRET)
                .hmac("date: " + date + "\n" + method + " " + uri + " HTTP/1.1"));
    // HMAC 授权
    String authorization = "hmac username=\"" + CUSTOMER_USERNAME + "\", algorithm=\"hmac-sha256\", headers=\"date request-line\", signature=\"" + signature + "\"";

    // 打印请求头
    System.out.println("Authorization: " + authorization);
    System.out.println("Date: " + date);
    // 发起请求
    /*OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url(BASE_URL + uri)
        .header("Authorization", authorization)
        .header("Date", date)
        .get()
        .build();
    try (Response response = client.newCall(request).execute()) {
      System.out.println("请求结果: " + response.body().string());
    } catch (IOException e) {
      e.printStackTrace();
    }*/
  }


  private static void postRequest(String uri, String body) throws NoSuchAlgorithmException {
    System.out.println("---------- POST ---------");

    String method = "POST";

    // 时间
    String date = RFC_7231_FORMATTER.format(ZonedDateTime.now(UTC));

    // 请求体摘要
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    byte[] hash = messageDigest.digest(body.getBytes(UTF_8));
    String digest = "SHA-256=" + encodeBase64String(hash);

    // 签名，计算方法为 hmac_sha_256(key, 日期 + 方法 + 路径 + 摘要)
    HmacUtils hmacSha256 = new HmacUtils(HMAC_SHA_256, CUSTOMER_SECRET);
    String signature = encodeBase64String(hmacSha256.hmac(
        "date: " + date + "\n" + method + " " + uri + " HTTP/1.1\ndigest: " + digest));

    // HMAC 授权
    String authorization = "hmac username=\"" + CUSTOMER_USERNAME + "\", algorithm=\"hmac-sha256\", headers=\"date request-line digest\", signature=\"" + signature + "\"";

    // 打印请求头
    System.out.println("Authorization: " + authorization);
    System.out.println("Date: " + date);
    System.out.println("Digest: " + digest);

    // 发起请求
   /* OkHttpClient client = new OkHttpClient();
    RequestBody requestBody = RequestBody.create(JSON, body);
    Request request = new Request.Builder()
        .url(BASE_URL + uri)
        .header("Authorization", authorization)
        .header("Date", date)
        .header("Digest", digest)
        .post(requestBody)
        .build();
    try (Response response = client.newCall(request).execute()) {
      System.out.println("请求结果: " + response.body().string());
    } catch (IOException e) {
      e.printStackTrace();
    }*/
  }
}
