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
    implementation 'com.github.JicuNull:MCReverse:e8ed501'
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
