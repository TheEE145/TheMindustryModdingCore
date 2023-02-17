public class OctoChainsaw {
    //the mod.json of my mod.
    //this data made at 2/17/2023 2:55 PM
    public static final String modJson = """
            {
              "displayName": "Solid Octo Chainsaw (SOC) mod",
              "name": "octo",
              "author": "TheEE145",
              "main": "octo.Octo",
              "description": "A Mindustry java modular mod with 16 java syntax. The mod adds new content in this game and countries",
              "version": 1.0,
              "minGameVersion": 136,
              "java": true,
              "contributors": [
                {
                  "name": "TheEE145",
                  "freesound": false
                },
                {
                  "name": "bubaproducer",
                  "freesound": true
                },
                {
                  "name": "wesleyextreme_gamer",
                  "freesound": true
                }
              ],
              "repository": {
                "name": "solid-octo-chainsaw-mod",
                "user": "TheEE145"
              },
              "bundleCreation": {
                "enabled": true,
                "scanType": {
                  "mods": false,
                  "local": true,
                  "named": false
                },
                "postTime": 5,
                "ignoredToScan": [
                  "octo-end",
                  "octo-substance43",
                  "octo-pentagonium",
                  "octo-magma-cube",
                  "octo-monolith",
                  "octo-earth-power",
                  "octo-exodus1x",
                  "octo-exodus1"
                ]
              }
            }
            """;

    public static final String modJsonNormal = """
            {
              "displayName": "Solid Octo Chainsaw (SOC) mod",
              "name": "octo",
              "author": "TheEE145",
              "main": "octo.Octo",
              "description": "A Mindustry java modular mod with 16 java syntax. The mod adds new content in this game and countries",
              "version": 1.0,
              "minGameVersion": 136,
              "java": true
            }
            """;
}