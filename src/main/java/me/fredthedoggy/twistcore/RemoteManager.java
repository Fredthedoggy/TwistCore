package me.fredthedoggy.twistcore;

import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RemoteManager {

    Class<?> managerClass;

    public RemoteManager() {
        managerClass = getManagerClass();
    }

    private Class<?> getManagerClass() {
        try {
            return Class.forName(TwistCore.getCurrentManager());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Could not find TwistCoreManager class!");
    }

    public void registerModule(LocalModule module) {
        try {
            Method registerMethod = managerClass.getMethod("register", Class.class, JavaPlugin.class);
            Method getManagerMethod = managerClass.getMethod("getInstance");
            registerMethod.invoke(getManagerMethod.invoke(null), module.getClass(), module);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerVariable(String name) {
        try {
            Method registerMethod = managerClass.getMethod("registerVariable", String.class);
            Method getManagerMethod = managerClass.getMethod("getInstance");
            registerMethod.invoke(getManagerMethod.invoke(null), name);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void setVariable(String name, String value) {
        try {
            Method registerMethod = managerClass.getMethod("setVariable", String.class, String.class);
            Method getManagerMethod = managerClass.getMethod("getInstance");
            registerMethod.invoke(getManagerMethod.invoke(null), name, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public String getVariable(String name) {
        try {
            Method registerMethod = managerClass.getMethod("getVariable", String.class);
            Method getManagerMethod = managerClass.getMethod("getInstance");
            return (String) registerMethod.invoke(getManagerMethod.invoke(null), name);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
