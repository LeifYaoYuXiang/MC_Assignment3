# Assignment 3
## Essential (80 points):
- User should be able to add arbitrary amount of modules to the running app using "Add module" dialogue. Mandatory fields: 加课程，有以下几个关键词
   - Module Code课程Code
   - Full Module Name课程名
   - Lecture/Practical choice L还是P
   - Day of Week 星期
   - Time - start time and end time 起始/截至时间
   - Location (room #) 房间号
   - Additional info (comments) 其他信息
It is up to you as a developer to determine the type of these fields. This information should be permanently stored in the application and survive application restarts. 永久化信息存储

- User should be able to display entered modules in a scrollable list. Scrollable list should contain the following information: 在Scrollable List中展示所有课程的信息
   - Module Code
   - First letter (whether this is P for practical or L for lecture)
   - Short indication of the day (Th for Thursday, Mo for Monday, etc)
   - Start time
   - Location, *e.g.* B1.06

- User should be able to click on any course to receive additional information for each course, which will display all the fields specified in 1. Clicking 'Back' should take user back to the list.对列表实现点击事件的监听，监听点击事件之后，允许使用者对点击的课程做出修改

- User should be able to remove any course previously entered to the app. Removal should present a confirmation dialogue to confirm the module information removal. Removing module information should delete this from the permanent storage, so it cannot appear again.

- Do not forget - the usual rules apply: your app should work on all two different types of phone
Typical phone: Nexus S (4", 480x800, hdpi)
High res phone: Nexus 7 (7.02", 1200x1920: xhdpi) ( TVDPI will also get full marks)
适配：hdpi xhdpi

## Optional (10+5+5 points):

- Implement notifications that can be enabled or disabled, which notifies you that certain course is about to start. Notifications should be configurable per module and offer options to notify 15, 10, 5, and 0 minutes in advance that the module is about to start. Notifications should be implemented using Android notifications framework (10 points max)
实现Notification，在上课前多久进行播报提醒
- Design a widget for your app that can be installed on the home screen and shows upcoming lectures and practical's (5 points max)
桌面插件（Widget）
- Add preferences where users can change application background colour, font colours and font itself. Give users at least 3 choices for each option (5 points max)
更换背景颜色

## 评分标准

- Add Module:
	All fields added Module Code Full Module Name Lecture/Practical choice Day of Week Time - start time and end time Location (room #) Additional info (comments)
- Display:
	Scrollable list or Grid view containing Module Code First letter (whether this is P for practical or L for lecture) Short indication of the day Start time Location
- Clickable list:
	User can click on the list or Grid to see all the information and the back button brings them back to display.
- Removing module:
	Can remove a module and its removal propagates throughout the app correctly.
- Support for multiple devices:
	Landscape / Portrait support and different screen sizes supported
- notifications:
	Fully implemented
- widget:
	Widget created
- Preferences:
	users can change application background colour, font colours and font itself.