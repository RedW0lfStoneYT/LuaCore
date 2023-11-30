
![Latest Build Tests](https://dev.selenadevelopment.com/job/LuaCore/badge/icon?subject=Latest%20Build)
![Latest version](https://repo.selenadevelopment.com/api/badge/latest/releases/dev/selena/lua/LuaCore?name=LuaCore)
[![Last successful build](https://img.shields.io/badge/dynamic/json?color=light_green&label=last%20successful%20build&query=%24.lastSuccessfulBuild.number&url=https%3A%2F%2Fdev.selenadevelopment.com%2Fjob%2FLuaCore%2Fapi%2Fjson)](https://dev.selenadevelopment.com/job/LuaCore/lastSuccessfulBuild)
[![Last failed build](https://img.shields.io/badge/dynamic/json?color=red&label=last%20failed%20build&query=%24.lastFailedBuild.number&url=https%3A%2F%2Fdev.selenadevelopment.com%2Fjob%2FLuaCore%2Fapi%2Fjson)](https://dev.selenadevelopment.com/job/LuaCore/lastFailedBuild)
# LuaCore
A Core for adding Lua to 1.20 plugins (Not a plugin itself just an API)

This API uses [LuaJava](https://github.com/luaj/luaj) to run Lua scripts in your Minecraft Spigot plugin, there is also a GSON config loader built in.
please refer to the wiki once its up for how to use.

[View Repo for how to add](https://repo.selenadevelopment.com/#/releases)

Be sure to add the following to your onEnable() method
`LuaCore.setPlugin(this);`

Wiki will be coming soon, working on [JavaDocs](docs.selenadevelopment.com) atm

You can view the example project used in the wiki examples [here](https://github.com/RedW0lfStoneYT/LuaCoreExample)
