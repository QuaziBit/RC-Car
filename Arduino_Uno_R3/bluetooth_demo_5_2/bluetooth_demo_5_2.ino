int curentSpeed = 0;
char incomingByte;

// Motor A
int motorSpeed_A = 9;
int pin8 = 8;
int pin7 = 7;
 
// Motor B
int motorSpeed_B = 3;
int pin5 = 5;
int pin4 = 4;

int pin11 = 11;
int pin6 = 6;
int ledPin = 13; 

void setup() 
{
  Serial.begin(115200);

  // Set all the motor control pins to outputs
  pinMode(motorSpeed_A, OUTPUT);
  pinMode(motorSpeed_B, OUTPUT);
  pinMode(pin8, OUTPUT);
  pinMode(pin7, OUTPUT);
  pinMode(pin5, OUTPUT);
  pinMode(pin4, OUTPUT);

  pinMode(ledPin, OUTPUT);
  digitalWrite(ledPin, LOW); 

  pinMode(pin11, OUTPUT);
  digitalWrite(pin11, HIGH); 

  pinMode(pin6, OUTPUT);
  digitalWrite(pin6, HIGH); 
}

void goForward()
{
  // This function will run the motors in one direction at a specific speed
  // Turn on motor A
  digitalWrite(pin8, HIGH);
  digitalWrite(pin7, LOW);

  // Turn on motor B
  digitalWrite(pin5, HIGH);
  digitalWrite(pin4, LOW);

  // Set motor speed
  analogWrite(motorSpeed_A, curentSpeed);
  analogWrite(motorSpeed_B, curentSpeed);

  digitalWrite(ledPin, HIGH);
}

void goBack()
{
  // This function will run the motors in one direction at a specific speed
  // Turn on motor A
  digitalWrite(pin8, LOW);
  digitalWrite(pin7, HIGH);

  // Turn on motor B
  digitalWrite(pin5, LOW);
  digitalWrite(pin4, HIGH);

  // Set motor speed
  analogWrite(motorSpeed_A, curentSpeed);
  analogWrite(motorSpeed_B, curentSpeed);

  digitalWrite(ledPin, HIGH);
}

void left()
{
  // Turn on motor A
  digitalWrite(pin8, HIGH);
  digitalWrite(pin7, LOW);

  // Turn on motor B
  digitalWrite(pin5, LOW);
  digitalWrite(pin4, HIGH);

  // Set motor speed
  analogWrite(motorSpeed_A, curentSpeed);
  analogWrite(motorSpeed_B, curentSpeed);

  digitalWrite(ledPin, HIGH);
}

void right()
{
  // Turn on motor A
  digitalWrite(pin8, LOW);
  digitalWrite(pin7, HIGH);

  // Turn on motor B
  digitalWrite(pin5, HIGH);
  digitalWrite(pin4, LOW);

  // Set motor speed
  analogWrite(motorSpeed_A, curentSpeed);
  analogWrite(motorSpeed_B, curentSpeed);

  digitalWrite(ledPin, HIGH);
}

void stopCar()
{
  // This function will stop the motors
  // Turn on motor A
  digitalWrite(pin8, LOW);
  digitalWrite(pin7, LOW);

  // Turn on motor B
  digitalWrite(pin5, LOW);
  digitalWrite(pin4, LOW);

  // Set motor speed
  analogWrite(motorSpeed_A, 0);
  analogWrite(motorSpeed_B, 0);

  digitalWrite(ledPin, LOW);
}

void setSpeed(int val)
{
  curentSpeed = val;
  digitalWrite(ledPin, HIGH);
  delay(50);  
  stopCar();
}

void loop() 
{
  if (Serial.available() > 0)
  {
    // read the incoming byte:
    incomingByte = Serial.read();

    //Serial.print("Received input bytes:");
    //Serial.println(incomingByte, DEC);
    //Serial.flush();
    
    if(incomingByte == 's')
    {
      stopCar();
      //Serial.println("OFF");
    }
    else if(incomingByte == 'g')
    {
      goForward();
      //Serial.println("GO");
    }
    else if(incomingByte == 'b')
    {
      goBack();
      //Serial.println("BACK");
    }
    else if(incomingByte == 'l')
    {
      left();
      //Serial.println("LEFT");
    }
    else if(incomingByte == 'r')
    {
      right();
      //Serial.println("RIGHT");
    }
    else if(incomingByte == '0')
    {
      setSpeed(0);
      //Serial.println("SPEED: 0");
    }
    else if(incomingByte == '1')
    {
      setSpeed(25);
      //Serial.println("SPEED: 1");
    }
    else if(incomingByte == '2')
    {
      setSpeed(50);
      //Serial.println("SPEED: 2");
    }
    else if(incomingByte == '3')
    {
      setSpeed(75);
      //Serial.println("SPEED: 3");
    }
    else if(incomingByte == '4')
    {
      setSpeed(100);
      //Serial.println("SPEED: 4");
    }
    else if(incomingByte == '5')
    {
      setSpeed(125);
      //Serial.println("SPEED: 5");
    }
    else if(incomingByte == '6')
    { 
      setSpeed(150);
      //Serial.println("SPEED: 6");
    }    
    else if(incomingByte == '7')
    {
      setSpeed(175);
      //Serial.println("SPEED: 7");
    }
    else if(incomingByte == '8')
    {
      setSpeed(200);
      //Serial.println("SPEED: 8");
    }
    else if(incomingByte == '9')
    {
      setSpeed(225);
      //Serial.println("SPEED: 9");
    }
    else
    {
      stopCar();
      //Serial.println("OFF");
    }
  } 
}

