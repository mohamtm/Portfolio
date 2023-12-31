# in3110_instapy #

## Summary
This package allows you to take an image of your choice and put either a gray filter or sepia filter over the image. You can choose to view this image in a terminal, or save the image to a file. You can also scale down the image by a factor of your choosing.

## Installation
To install the package, all you need is access to the files in this folder. Afterwards put it in a location of your choosing. Finally, run the command:  
```python3 -m pip install [PATH TO PACKAGE]```

## Running instructions
(Example: "python3 -m in3110_instapy FILE" will apply a gray filter to the image.)  
(Example: "python3 -m in3110_instapy FILE -se FACTOR" will apply a sepia filter to the image with a factor that must be between 0 and 1)  
You can run the package with:  

1. ```python3 -m in3110_instapy file <options>```
2. ```instapy file <options>``` 

Required argument:  
file [Path to image you want to filter]

Options:  
-h, --help [Shows usage instructions]  
-o OUT, --out OUT [Output filename]  
-g, --gray [Selects the gray filter]  
-se SEPIA, --sepia SEPIA [Selects the sepia filter with tuning factor. Tuning only comes into play for numpy implementation,]  
-sc SCALE, --scale SCALE [Scale factor to resize the image]  
-i, --implementation {python, numpy, numba} [Implementation of the filtering function]  
-r, --runtime [Lets you find the average runtime over 3 runs]

Default running of the package is a grayfilter with the python implementation. Default running will not rescale the image or save the output to a file, nor will it show the runtime.