# RxBox
v0.0.1[WIP]  
This library makes [dropbox-java-sdk](https://github.com/dropbox/dropbox-sdk-java) easy to use with RxJava.  

## How to use

1. Clone this reposiroty.  
2. Run gradlew task.  

```
$ ./gradlew jar
```

3. Please import `rxbox/build/libs/rxbox.jar` into your project.
  
Please wait until I register with jcenter.  
I will do it as soon as possible, sorry.

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

* add jCenter throw bintray.
* implementation more method.
* separate API_KEY from `String.xml`.
* create Javadoc.