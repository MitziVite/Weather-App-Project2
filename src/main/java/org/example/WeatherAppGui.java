package org.example;

import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;

    public WeatherAppGui(){
        // setup our gui and add a title
        super("Weather App");

        // configure gui to end the program's process once it has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // set the size of our gui (in pixels)
        setSize(450, 800);

        // load our gui at the center of the screen
        setLocationRelativeTo(null);

        // make our layout manager null to manually position our components within the gui
        setLayout(null);

        // prevent any resize of our gui
        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents(){
        // search field
        JTextField searchTextField = new JTextField();

        // set the location and size of our component
        searchTextField.setBounds(15, 15, 351, 45);

        // change the font style and size
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(searchTextField);

        // weather image
        URL url = getClass().getClassLoader().getResource("images/cloudy.png");
        JLabel weatherConditionImage = new JLabel(loadImage(url));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        // temperature text
        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));

        // center the text
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        // weather condition description
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 405, 450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        // humidity image
        URL humidityUrl = getClass().getClassLoader().getResource("images/humidity.png");
        JLabel humidityImage = new JLabel(loadImage(humidityUrl));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);

        // humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        // windspeed image
        URL windspeedUrl = getClass().getClassLoader().getResource("images/windspeed.png");
        JLabel windspeedImage = new JLabel(loadImage(windspeedUrl));
        windspeedImage.setBounds(220, 500, 74, 66);
        add(windspeedImage);

        // windspeed text
        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);

        // visibility image
        URL visibilityUrl = getClass().getClassLoader().getResource("images/visibility.png");
        JLabel visibilityImage = new JLabel(loadImage(visibilityUrl));
        visibilityImage.setBounds(15, 600, 74, 66);
        add(visibilityImage);

        // visibility text
        JLabel visibilityText = new JLabel("<html><b>Visibility</b> 15km/h</html>");
        visibilityText.setBounds(200, 600, 200, 55);
        visibilityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(visibilityText);

        // search button
        URL searchUrl = getClass().getClassLoader().getResource("images/search.png");
        JButton searchButton = new JButton(loadImage(searchUrl));

        // change the cursor to a hand cursor when hovering over this button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get location from user
                String userInput = searchTextField.getText();

                // validate input - remove whitespace to ensure non-empty text
                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                // retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                // update gui

                // update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");

                // depending on the condition, we will update the weather image that corresponds with the condition
                switch(weatherCondition){
                    case "Clear":
                        URL url = getClass().getClassLoader().getResource("images/clear.png");
                        weatherConditionImage.setIcon(loadImage(url));
                        break;
                    case "Cloudy":
                        URL urlCloudy = getClass().getClassLoader().getResource("images/cloudy.png");
                        weatherConditionImage.setIcon(loadImage(urlCloudy));
                        break;
                    case "Rain":
                        URL urlRain = getClass().getClassLoader().getResource("images/rain.png");
                        weatherConditionImage.setIcon(loadImage(urlRain));
                        break;
                    case "Snow":
                        URL urlSnow = getClass().getClassLoader().getResource("images/snow.png");
                        weatherConditionImage.setIcon(loadImage(urlSnow));
                        break;
                }

                // update temperature text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " C");

                // update weather condition text
                weatherConditionDesc.setText(weatherCondition);

                // update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                // update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");

                //update visibility text
                double visibility = (double) weatherData.get("visibility");
                visibilityText.setText("<html><b>Visibility<b>" + visibility + "</html>");
            }
        });
        add(searchButton);
    }

    // used to create images in our gui components
    private ImageIcon loadImage(URL resourcePath){
        try{
            // read the image file from the path given
            BufferedImage image = ImageIO.read(resourcePath);

            // returns an image icon so that our component can render it
            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;
    }
}









