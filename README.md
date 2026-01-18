# InfiniteParkour

A procedural, infinite parkour plugin for Minecraft (Paper/Spigot). This plugin generates platforms dynamically as you jump, challenging you to see how many blocks you can reach without falling!

## 📺 Demo

| Third-Person View | First-Person View |
| :---: | :---: |
| ![Third Person Demo](imgs/demo_final.gif) | ![FPV Demo](imgs/FPV_demo.gif) |

*If the video doesn't play automatically, you can find the demo file in the `/imgs` folder.*

## ✨ Features

*   **Procedural Generation:** Platforms are created in real-time as you progress.
*   **Automatic Cleanup:** Previous platforms are removed as you move forward to keep the world clean.
*   **Smart Detection:** Uses radius and height-based detection to ensure jumps feel snappy and accurate.
*   **Score Tracking:** Keep track of your current streak in the chat.
*   **Fail Protection:** If you fall, you are automatically teleported back to your starting position, and all spawned blocks are cleared.

