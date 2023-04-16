package me.fredthedoggy.twistcore;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwistCoreManager {
    private static TwistCoreManager twistCoreManager;
    private List<RemoteModule> modules;
    private Map<String, String> variables;

    TwistCoreManager() {
        this.modules = new ArrayList<>();
        this.variables = new HashMap<>();
        twistCoreManager = this;
        this.registerVariable("runner");
    }

    public static TwistCoreManager getInstance() {
        return twistCoreManager;
    }

    public void register(Class<? extends JavaPlugin> moduleClass, JavaPlugin module) {
        modules.add(new RemoteModule(moduleClass, module));
    }

    public List<RemoteModule> getModules() {
        return modules;
    }

    public void registerVariable(String name) {
        variables.putIfAbsent(name, null);
    }

    public void setVariable(String name, String value) {
        variables.put(name, value);
    }

    public String getVariable(String name) {
        return variables.get(name);
    }
}
