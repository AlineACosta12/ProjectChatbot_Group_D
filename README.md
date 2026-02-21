# ProjectChatbot



## Getting started

To make it easy for you to get started with GitLab, here's a list of recommended next steps.

Already a pro? Just edit this README.md and make it your own. Want to make it easy? [Use the template at the bottom](#editing-this-readme)!

## Add your files

- [ ] [Create](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#create-a-file) or [upload](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#upload-a-file) files
- [ ] [Add files using the command line](https://docs.gitlab.com/ee/gitlab-basics/add-file.html#add-a-file-using-the-command-line) or push an existing Git repository with the following command:

```
cd existing_repo
git remote add origin https://gitlab.griffith.ie/aline.andradecosta/projectchatbot.git
git branch -M main
git push -uf origin main
```

## Integrate with your tools

- [ ] [Set up project integrations](https://gitlab.griffith.ie/aline.andradecosta/projectchatbot/-/settings/integrations)

## Collaborate with your team

- [ ] [Invite team members and collaborators](https://docs.gitlab.com/ee/user/project/members/)
- [ ] [Create a new merge request](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html)
- [ ] [Automatically close issues from merge requests](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#closing-issues-automatically)
- [ ] [Enable merge request approvals](https://docs.gitlab.com/ee/user/project/merge_requests/approvals/)
- [ ] [Set auto-merge](https://docs.gitlab.com/ee/user/project/merge_requests/merge_when_pipeline_succeeds.html)

## Test and Deploy

Use the built-in continuous integration in GitLab.

- [ ] [Get started with GitLab CI/CD](https://docs.gitlab.com/ee/ci/quick_start/index.html)
- [ ] [Analyze your code for known vulnerabilities with Static Application Security Testing (SAST)](https://docs.gitlab.com/ee/user/application_security/sast/)
- [ ] [Deploy to Kubernetes, Amazon EC2, or Amazon ECS using Auto Deploy](https://docs.gitlab.com/ee/topics/autodevops/requirements.html)
- [ ] [Use pull-based deployments for improved Kubernetes management](https://docs.gitlab.com/ee/user/clusters/agent/)
- [ ] [Set up protected environments](https://docs.gitlab.com/ee/ci/environments/protected_environments.html)

***

# Wardrobot — Weather & Clothing Trip Planner Chatbot (Group D)

Wardrobot is a Java chatbot that helps users plan what to wear for a trip by fetching real-time weather data for multiple locations and generating clothing recommendations based on conditions (e.g., rain, cold, clear) and context such as time of day. :contentReference[oaicite:1]{index=1}

## Project Purpose
This project was built to support users planning a trip (up to **5 locations across 3 days**) by providing:
- Weather information per location/date
- Practical clothing suggestions (e.g., umbrella for rain, warm layers for cold weather)
- A friendly, guided conversation flow (console + GUI) :contentReference[oaicite:2]{index=2}

## Key Features
- **Weather lookup** using a third-party Weather API (Open-Meteo) :contentReference[oaicite:3]{index=3}
- **Location → coordinates** support via geolocation handling (so users can type place names) :contentReference[oaicite:4]{index=4}
- **Clothing recommendations** based on weather conditions and temperature (rain/snow/clear/windy, etc.) :contentReference[oaicite:5]{index=5}
- **Multi-location and forecast handling** (designed around the “trip planning” scenario) :contentReference[oaicite:6]{index=6}
- **JavaFX GUI** for improved user interaction (alongside a console flow) :contentReference[oaicite:7]{index=7}
- **JUnit 5 test suite** covering WeatherAPI, chatbot flow, and clothing recommendation logic :contentReference[oaicite:8]{index=8}

## Tech Stack
- **Java**
- **JavaFX** (GUI)
- **Open-Meteo Weather API** (weather data) :contentReference[oaicite:9]{index=9}
- **org.json** (JSON parsing) :contentReference[oaicite:10]{index=10}
- **JUnit 5** (unit testing) :contentReference[oaicite:11]{index=11}

## High-Level Architecture
Main components (as described in the documentation): :contentReference[oaicite:12]{index=12}
- `Chatbot` — manages conversation flow and user input
- `WeatherAPI` — requests and parses weather data (JSON) from the external API
- `ClothingRecommender` — converts weather info into clothing suggestions
- Test classes (JUnit 5):
  - `ChatbotTest`
  - `WeatherAPITest`
  - `ClothingRecommenderTest`

## How to Run

### Prerequisites
- Java JDK installed (recommended: JDK 17+)
- Internet connection (API calls require it)
- JavaFX configured (if running the GUI)
- org.json library available in the project dependencies :contentReference[oaicite:13]{index=13}

### Option A — Run the Console Version
1. Open the project in your IDE (e.g., IntelliJ / Eclipse).
2. Locate the `Chatbot` class.
3. Run the `main()` method.
4. Follow the prompts:
   - Enter location(s)
   - The chatbot fetches weather and outputs clothing recommendations :contentReference[oaicite:14]{index=14}

### Option B — Run the JavaFX GUI
1. Ensure JavaFX is configured in your IDE/project.
2. Locate the GUI entry class (commonly `ChatbotGUI.java`).
3. Run the GUI class.
4. Use the interface to enter locations and receive weather + clothing suggestions. :contentReference[oaicite:15]{index=15}

## Running Tests
This project includes **JUnit 5** tests that verify:
- Valid/invalid location handling in the Weather API
- Clothing recommendations for different conditions (sunny/rainy/cold + time-of-day scenarios)
- End-to-end chatbot behaviour checks :contentReference[oaicite:16]{index=16}

Run tests from your IDE’s test runner (JUnit) or your build tool if configured.

## Version Control Workflow (Team)
- Stable code in `main`
- Feature work in separate branches per developer (GUI / Weather API / Recommendations)
- Frequent commits, code reviews, and milestone-based development :contentReference[oaicite:17]{index=17}

## Future Improvements (Ideas)
Some enhancements described/planned across milestones include:
- richer UI/UX and responsiveness
- improved forecast detail (e.g., wind/humidity/conditions)
- more personalised suggestions (wardrobe-based recommendations)
- optional accessibility features (speech-to-text) :contentReference[oaicite:18]{index=18}

## Authors
- Aline Andrade Costa
- Cynthia da Silva Roque
- Sergio Alves da Silva :contentReference[oaicite:19]{index=19}

## License
- For academic projects

## Project Status
- Completed as a second-year Software Development / Server-Side related team project.
- Further improvements may be added in future iterations.
