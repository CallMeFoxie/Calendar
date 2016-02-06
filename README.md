# CalendarAPI #
This mod is not actually much of a mod, it is a universal API for other modders to use.

## Modpacks ##
Since this is an API you will need to distribute it in a modpack so yes, you have my blessing!

The only thing I dislike is rehosting my mod on 3rd party websites. If you want to link my mod then link the Curse page, do NOT rehost it yourself. (Modpack rehosting is fine of course.)

## Developer Environment ##
If you want to do some work on this mod OR want just to load it in your development environment for any reason, you can either:

a) get precompiled versions from my Jenkins [(1.7.10)](http://jenkins.foxiehost.eu/job/Calendar%20-%201.7.10/) [(1.8.9)](http://jenkins.foxiehost.eu/job/Calendar/) - note that versions here do not match versions on Curse, the ones on Curse do not include build number! (The versions themselves do though, as you can check in mcmod.info.)

b) use Maven

in your build.gradle:

```
repositories {
    maven {
        name "Foxiehost"
        url "http://maven.foxiehost.eu"
    }
}
dependencies {
    compile "foxie:Calendar:1.8.9-1.0-10:deobf" // update for selected MC version and build
}
```

c) set it in your dev environment yourself:

in command line:

```
gradlew setupDecompWorkspace
```

If you want to use IntelliJ IDEA or Eclipse you can either import the project or run

```
gradlew idea # IDEA
gradlew eclipse # Eclipse
```

Now your development environment should be ready to run my mod.

## Bug Reports and Feature Requests ##
Both should be done in Issues here on GitHub. When you are reporting a crash, DEFINITELY include a crashlog and preferably even the main log itself (so it includes the list of other mods you are running etc). If you did something special, DESCRIBE IT! If you are reporting a gameplay bug, DESCRIBE IT!

Feature Requests should be as descriptive as possible.

Language Pull Requests are no problem.

Code Pull Requests will be checked, make sure you know what you're doing!
