import time
import cv2
import numpy as np 
  
# define a video capture object 
vid = cv2.VideoCapture(0)
# vid.set(cv2.CAP_PROP_FRAME_WIDTH, 1280)
# vid.set(cv2.CAP_PROP_FRAME_HEIGHT, 720)

frameHistory = 30
lastXFrames = []

for i in range(frameHistory):
    lastXFrames.append(None)

contrast = 1.5
brightness = 1

overlayTransparency = 0.1

while(True):
    # Capture the video frame by frame 
    ret, frame = vid.read()
    
    # frame = cv2.colorChange(frame, cv2.COLOR_BGR2RGB)
    
    # Keep the last bunch of frames
    for i in range(frameHistory - 1, 0, -1):
        lastXFrames[i] = lastXFrames[i - 1]

    # Copy the current frame to the first frame
    lastXFrames[0] = frame.copy()
        
    # Display the original frame
    cv2.imshow('original', frame)
    
    grayFrame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    
    channels = [None, None, None]
    channels[0] = np.zeros((frame.shape[0], frame.shape[1]), dtype = np.uint8)
    channels[1] = np.zeros((frame.shape[0], frame.shape[1]), dtype = np.uint8)
    channels[2] = np.zeros((frame.shape[0], frame.shape[1]), dtype = np.uint8)
    
    for e in range(3):
        index = (frameHistory // (e + 1)) - 1
        
        # Make sure the oldest frame is not None
        if lastXFrames[index] is not None:
            # Get the oldest frame
            oldestFrame = cv2.cvtColor(lastXFrames[index], cv2.COLOR_BGR2GRAY)
            
            # Invert the oldest frame
            oldestFrame = cv2.bitwise_not(oldestFrame)
            
            # Overlay the inverted with a 50% transparency over the current frame
            channels[e] = cv2.addWeighted(grayFrame, overlayTransparency, oldestFrame, 1 - overlayTransparency, 0)
            
            # Display the original frame
            cv2.imshow('channel' + str(e), channels[e])
    
    frame = cv2.merge((channels[0], channels[1], channels[2]))
    
    frame = cv2.bitwise_not(frame)
    
    # Display the frame
    cv2.imshow('currentFrame', frame)
      
    # the 'q' button is set as the quit button
    if cv2.waitKey(1) & 0xFF == ord('q'): 
        break
    # elif cv2.waitKey(1) & 0xFF == ord('a'):
    #     frameHistory = min(30, frameHistory + 1)
        
    #     # Reset the lastXFrames array
    #     lastXFrames = []
    #     for i in range(frameHistory):
    #         lastXFrames.append(None)
        
    #     print(frameHistory)
    # elif cv2.waitKey(1) & 0xFF == ord('s'):
    #     frameHistory = max(1, frameHistory - 1)
        
    #     # Reset the lastXFrames array
    #     lastXFrames = []
    #     for i in range(frameHistory):
    #         lastXFrames.append(None)
            
    #     print(frameHistory)
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