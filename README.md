Did you ever had your server refuse to shut down after running **/stop**? Annoying, right? That’s because some mod developers forgets to close async threads, preventing the server from shutting down properly.

**NMZT** fixes this by forcefully terminating any lingering "zombie" threads **after** the world and playerdata have been safely saved. No more manually killing your server process after every restart or crash — **NMZT** handles it for you. 
## ✅ Features:
- Fully automatic. You can literally just put the mod in your mods folder and completely forget about this problem. ⚙️
- Lightweight. This mod implements only 2 hooks into the vanilla code and nothing else, so it will NOT reduce the server performance. 🪶
- Safe. NMZT is 99.9% compatible with other mods, so I don't think it can break your world or modpack. 🛡️

Modrinth download: https://modrinth.com/mod/nmzt

![Woah! Zombie threads have been terminated by NMZT](https://cdn.modrinth.com/data/cached_images/daa8c3a0bf80a8ba2a0855d687cc9dc4cc08c99e.png)
