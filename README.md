# android-beacons
Android app to show local informations using beacons detection

Using android-beacon-library, Firebase and ripplebackground library of https://github.com/skyfishjy

Icons made by Wichai.wi from www.flaticon.com

You need to add .app/google-services.json with your information in Firebase project and your api key

And also you need to change your api_key in google_maps_api.xml

```

{
  "project_info": {
    "project_number": "YOUR_FIREBASE_PROJECT_NUMBER",
    "firebase_url": "YOUR_FIREBASE_URL",
    "project_id": "YOUR_FIREBASE_PROJECT_ID",
    "storage_bucket": "YOUR_FIREBASE_PROJECT_STORAGE_BUCKET"
  },
  "client": [
    {
      "client_info": {
        "mobilesdk_app_id": "1:721570747475:android:901837d3f6c907a09ce2a6",
        "android_client_info": {
          "package_name": "es.ulpgc.alexmoreno.beacons"
        }
      },
      "oauth_client": [
        {
          "client_id": "721570747475-6b3bdti6ts0m0cvkuhccioe4hmromr9t.apps.googleusercontent.com",
          "client_type": 1,
          "android_info": {
            "package_name": "es.ulpgc.alexmoreno.beacons",
            "certificate_hash": "e0a73b32a017c5d71b7e757c0468da7bffcfc35d"
          }
        },
        {
          "client_id": "721570747475-ea59lo2h2qkogvp297pc3qv5deocivb4.apps.googleusercontent.com",
          "client_type": 3
        }
      ],
      "api_key": [
        {
          "current_key": "YOUR_API_KEY"
        }
      ],
      "services": {
        "appinvite_service": {
          "other_platform_oauth_client": [
            {
              "client_id": "721570747475-ea59lo2h2qkogvp297pc3qv5deocivb4.apps.googleusercontent.com",
              "client_type": 3
            }
          ]
        }
      }
    }
  ],
  "configuration_version": "1"
}
      
