# MCReverse
 Reverse engineered implementation of the **german** McDonald's Android API in Java

### Used by McCoupon
Please feel free to check out and use my website **[McCoupon.deals](https://www.mccoupon.deals)** the next time you 
visit McDonald's to get access to basically unlimited amounts of coupons without using the app :)

### Gradle
```java
allprojects {
    repositories {
	...
        maven { url 'https://jitpack.io' }
    }
}
```
```java
dependencies {
    implementation 'com.github.JicuNull:MCReverse:5fd8ed6'
}
```
Find more options here: **[Jitpack](https://jitpack.io/#JicuNull/MCReverse)**

## Connect and login
`McClient` connects to the McDonald's backend server and is used for all interactions with the server.

```java
McClient client = new McClient();
```
You can log in with an existing account by calling `login(email, password)`.
```java
McClient client = new McClient();
client.login("test@example.org", "123456");
```
