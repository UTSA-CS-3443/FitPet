## **FitPet**
A health tracking app with a virtual pet companion that motivates you to meet your daily wellness goals.

## **Motivation**
FitPet was designed to make health tracking fun and engaging by combining fitness goals with pet care. The app features a virtual pet whose happiness depends on whether you meet your daily health targets for food, water, exercise, and sleep. This gamification approach transforms routine health tracking into an enjoyable experience that motivates users to maintain healthy habits. The app provides a simple, intuitive way to log daily activities while receiving immediate feedback through your pet's mood, making wellness goals feel more achievable and rewarding.

## **Code Style**
The language used to write this app is **Java** with **XML** for Android layouts.

## **Video Demonstration**
[FitPet Demo Video](https://drive.google.com/file/d/1reRHDCmxa8x4iSiomtjS7YasO9vgiE9w/view?usp=sharing)

## **Tech Used**
We used **Android Studio** to code the app, **GitHub** for version control, and **XML** for designing the user interface layouts. The app utilizes **SharedPreferences** for storing user goals and settings, and **file I/O** for saving daily progress summaries.

## **Features**
- **Customizable Goals**: Set personalized targets for calories, water intake, sleep hours, and exercise
- **Activity Logging**: Easy-to-use interfaces for logging food (with macro tracking), water consumption, sleep hours, and exercise activities
- **Pet Companion**: Virtual pet that becomes happy when you meet your goals and sad when you don't
- **Input Validation**: Comprehensive error checking with helpful toast messages for all user inputs
- **Progress Tracking**: Real-time goal progress updates and daily summary reports
- **Data Persistence**: Automatic saving of daily progress to files for record keeping

## **Known Issues**
- Daily progress files accumulate without automatic cleanup

## **Installation**
The app is not available on the app store, but with access to the source code, you can run the app via Android Studio:
1. Clone or download the project repository
2. Open the project in Android Studio
3. Build and run the app on an Android device or emulator

## **How to use**
The app is straightforward and user-friendly:

1. **Initial Setup**: When first opening the app, you'll be prompted to set your pet's name and daily goals for calories, water, sleep, and exercise
2. **Home Screen**: View your pet and navigate to different tracking activities using the bottom navigation buttons
3. **Food Tracking**: Log meals with calories and optional macro information (fats, carbs, protein)
4. **Water Tracking**: Add water consumption in ounces throughout the day
5. **Sleep Tracking**: Enter hours of sleep from the previous night
6. **Exercise Tracking**: Log exercise activities with type, duration, and calories burned
7. **Goal Management**: Update your goals and save daily progress through the settings screen
8. **Pet Feedback**: Watch your pet's mood change based on whether you meet your daily goals

## **Accessing Saved Progress Files**
The app automatically saves your daily progress to text files for record keeping:

1. **File Location**: Progress files are saved to your device's internal storage in the app's private directory
2. **File Name**: `daily_summaries.txt`
3. **Accessing Files**: 
   - Use the "Save Today's Progress" button in the Goals/Settings screen
   - Files can be accessed through Android Studio's Device File Explorer
   - Path: `/data/data/edu.utsa.cs3443.fitpetdraft1/files/daily_summaries.txt`
4. **File Content**: Contains date, goals, actual values, and goal completion status for each day

## **Credits**
Micheal DeWitt, Bella Rodriguez, Sofia Galindo, Jose Ramos-Rodriguez


