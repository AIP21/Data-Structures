import time
import cv2
import numpy as np 
  
# define a video capture object 
vid = cv2.VideoCapture(0)
# vid.set(cv2.CAP_PROP_FRAME_WIDTH, 1280)
# vid.set(cv2.CAP_PROP_FRAME_HEIGHT, 720)

frameHistory = 6
lastXFrames = []

for i in range(frameHistory):
    lastXFrames.append(None)

contrast = 1.5
brightness = 1

overlayTransparency = 0.5

while(True):
    # Capture the video frame by frame 
    ret, frame = vid.read()
    
    # Keep the last bunch of frames
    for i in range(frameHistory - 1, 0, -1):
        lastXFrames[i] = lastXFrames[i - 1]
    
    # Increase the contrast
    # frame = cv2.convertScaleAbs(frame, alpha = contrast, beta = brightness)

    # Make it grayscale
    frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    
    # Display the original frame
    cv2.imshow('original', frame)

    # Copy the current frame to the first frame
    lastXFrames[0] = frame.copy()
    
    # Make sure the oldest frame is not None
    if lastXFrames[frameHistory - 1] is not None:
        # Get and invert the oldest frame
        oldestFrame = cv2.bitwise_not(lastXFrames[frameHistory - 1])
        
        # Overlay the inverted with a 50% transparency over the current frame
        frame = cv2.addWeighted(oldestFrame, overlayTransparency, frame, 1 - overlayTransparency, 0)
    
    # Display the frame
    cv2.imshow('currentFrame', frame)
      
    # the 'q' button is set as the quit button
    if cv2.waitKey(1) & 0xFF == ord('q'): 
        break
    elif cv2.waitKey(1) & 0xFF == ord('a'):
        frameHistory = min(30, frameHistory + 1)
        
        # Reset the lastXFrames array
        lastXFrames = []
        for i in range(frameHistory):
            lastXFrames.append(None)
        
        print(frameHistory)
    elif cv2.waitKey(1) & 0xFF == ord('s'):
        frameHistory = max(1, frameHistory - 1)
        
        # Reset the lastXFrames array
        lastXFrames = []
        for i in range(frameHistory):
            lastXFrames.append(None)
            
        print(frameHistory)
    # elif cv2.waitKey(1) & 0xFF == ord('z'):
    #     contrast *= 1.1
    #     # brightness *= 1.1
        
    #     print(contrast)
    # elif cv2.waitKey(1) & 0xFF == ord('x'):
    #     contrast *= 0.9
    #     # brightness *= 0.9
        
    #     print(contrast)
    
    # time.sleep(0.5)
  
# After the loop release the cap object 
vid.release()

# Destroy all the windows 
cv2.destroyAllWindows() 