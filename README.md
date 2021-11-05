# MCReverse
 Reverse engineered implementation of the McDonald's Android api in Java

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
    implementation 'com.github.JicuNull:MCReverse:d9abc9c'
}
```
Find more options here: **[Jitpack](https://jitpack.io/#JicuNull/MCReverse)**

## Connect and login
`MacClient` connects to the McDonald's backend server and is used for all interactions with the server.

```java
MacClient client = new MacClient();
```
You can log in with an existing account by calling `login(email, password)`.
```java
MacClient client = new MacClient();
client.login("test@example.org", "123456");
```
