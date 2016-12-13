# RxBox
v0.0.1[WIP]  
This library makes [dropbox-java-sdk](https://github.com/dropbox/dropbox-sdk-java) easy to use with RxJava.  

## Including In Project For Gradle
  
repository:    
  
```gradle
repositories {
    maven { url 'http://yagi2.github.io/RxBox/' }
}
```

dependency:    
  
``` gradle
dependencies {
    compile 'com.yagi2:rxbox:0.0.1'
}
```

## Including In Project For Maven (unconfirmed)  
  
repository:

```xml
<repositories>
    <repository>
        <id>yagi2-rxbox-repo</id>
        <url>http://yagi2.github.io/RxBox/</url>
    </repository>
</repositories>
```

dependency:

```xml
<dependency>
    <groupId>com.yagi2</groupId>
    <artifactId>rxbox</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Example
if you want get [FullAccount](https://dropbox.github.io/dropbox-sdk-java/api-docs/v2.1.x/com/dropbox/core/v2/users/FullAccount.html).
  
in java project  

```java
RxBox.getCurrentAccount(DbxClientV2 client)
    .toSingle()
    .subscribe(new SingleSubscriber<FullAccount> {
        @Override
        public void onSuccess(FullAccount account) {
          // You can use account data here.
        }

        @Override
        public void onError(Throwable error) {
          // Error
        }
    }); 
```

in android project(use with RxAndroid, RxLifecycle)  

```java
RxBox.getCurrentAccount(DbxClientV2 client)
    .subscribeOn(Schedules.io())
    .toSingle()
    .compose(bindToLifecycle().<FullAccount>forSingle())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(new SingleSubscriber<FullAccount> {
        @Override
        public void onSuccess(FullAccount account) {
          // You can use account data here.
        }

        @Override
        public void onError(Throwable error) {
          // Error
        }
    });
```

## Sample for Android
Please get Full Dropbox API Key [Developers - Dropbox](https://www.dropbox.com/developers/apps/create)  
then, please replace "API_KEY" in `values/strings.xml` and `AndroidManifest.xml`.

```gradlew
$ ./gradlew clean assembleDebug
```  

## TODO

* implementation more method.
* separate API_KEY from `String.xml`.
* create Javadoc.
* add tests.