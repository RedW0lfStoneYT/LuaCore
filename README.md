![Latest version](https://repo.selenadevelopment.com/api/badge/latest/releases/dev/selena/lua/LuaCore?name=LuaCore)
[![Last successful build](https://img.shields.io/badge/dynamic/json?color=light_green&label=last%20successful%20build&query=%24.lastSuccessfulBuild.number&url=https%3A%2F%2Fdev.selenadevelopment.com%2Fjob%2FLuaCore%2Fapi%2Fjson)](https://dev.selenadevelopment.com/job/LuaCore/lastSuccessfulBuild)
[![Last failed build](https://img.shields.io/badge/dynamic/json?color=red&label=last%20failed%20build&query=%24.lastFailedBuild.number&url=https%3A%2F%2Fdev.selenadevelopment.com%2Fjob%2FLuaCore%2Fapi%2Fjson)](https://dev.selenadevelopment.com/job/LuaCore/lastFailedBuild)  
[![Latest tests passed](https://img.shields.io/badge/dynamic/json?color=light_green&label=Latest%20tests%20passed&query=%24.passCount&url=https%3A%2F%2Fdev.selenadevelopment.com/job/LuaCore/lastBuild/testReport/api/json)](https://dev.selenadevelopment.com/job/LuaCore/lastBuild/testReport)
[![Latest tests failed](https://img.shields.io/badge/dynamic/json?color=red&label=Latest%20tests%20failed&query=%24.failCount&url=https%3A%2F%2Fdev.selenadevelopment.com/job/LuaCore/lastBuild/testReport/api/json)](https://dev.selenadevelopment.com/job/LuaCore/lastBuild/testReport)
# LuaCore
A library for easy development of plugins and for adding Lua to 1.21.5 plugins (Not a plugin itself just a lib)

This Library uses [LuaJava](https://github.com/luaj/luaj) to run Lua scripts in your Minecraft Spigot plugin, there is also a GSON config loader built in.
please refer to the [wiki](https://github.com/RedW0lfStoneYT/LuaCore/wiki) for started guides on how to use.

[View Repo for how to add](https://repo.selenadevelopment.com/#/releases)

Be sure to add the following to your onEnable() method
`LuaCore.setupCore(this);`

You can view all available methods on the [JavaDocs](https://docs.selenadevelopment.com).

You can view the example project used in the wiki examples [here](https://github.com/RedW0lfStoneYT/LuaCoreExample) (Slightly dated)
