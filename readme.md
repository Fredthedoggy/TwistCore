# Twistcore
What is Twistcore? Twistcore is a library that allows different plugins to communicate with each other, and allows for
these twists to be disabled and enabled without having to restart the server.

All twists are managed from a single gui (`/twist`), and twistcore automatically detects other instances of twistcore, determines a manager, and allows all plugins with twistcore to communicate with eachother.

# Developers

## How to add TwistCore to your plugin

### All Build Tools

1. Start by installing TwistCore to your local Maven repository, by running the gradle task `publishToMavenLocal`
2. Continue to your Build Tool's instructions below ([Maven](#maven), [Gradle](#gradle) (Comming Soon!))

### Maven

First, add the dependency to your `pom.xml`

```xml

<dependency>
    <groupId>me.fredthedoggy</groupId>
    <artifactId>TwistCore</artifactId>
    <version>1.0</version>
    <scope>compile</scope>
</dependency>
```

Then relocate it using the Maven Shade Plugin, Making sure to replace `[package]` with the relevant package

```xml

<relocations>
    <relocation>
        <pattern>me.fredthedoggy.twistcore</pattern>
        <shadedPattern>[package].twistcore</shadedPattern>
    </relocation>
</relocations>
```

Make sure you're using at least this version of the Maven Shade Plugin

```xml

<version>3.3.0-SNAPSHOT</version>
```

## How to use TwistCore

First, you'll need to change your plugin to extend `LocalModule` instead of `JavaPlugin`

Then, in your `onEnable()` add

```java
TwistCore.init(this);
```

and in your onDisable()` add

```java
TwistCore.disable();
```

After that, instead of using your onEnable() and onDisable() methods, you'll need to override the `TwistCore` methods:

```java
/**
 * Returns the module's icon for the GUI
 */
@Override
public ItemStack getIcon(){
        return new ItemStack(Material.DIAMOND);
}

/**
 * Run when the module is enabled
 */
@Override
public void onModuleEnable(){

}

/**
 * Run when the module is disabled
 */
@Override
public void onModuleDisable(){

}
```

Lastly, you can use features provided by TwistCore, like `TwistCore.adventure()` to get an instance of adventure,
or `TwistCore.remoteManager().getVariable("runner")` to get a variable from TwistCore (`runner` returns the UUID of the
speedrunner as a `string`)