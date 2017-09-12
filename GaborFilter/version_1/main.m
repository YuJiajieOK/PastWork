clear all
close all
clc
tic

I = imread('cameraman.jpg');
I = double(I);

 f1 = gabor_filter(0.25,0.5,0,I);
  f2 = gabor_filter(0.25,0.5,pi/4,I);
 f3 = gabor_filter(0.25,0.5,3*pi/4,I);
 f4 = gabor_filter(0.25,0.5,pi/2,I);
% f5 = gabor_filter(0.75,1.5,5*pi/4,I);
% f6 = gabor_filter(0.75,1.5,3*pi/2,I);
% f7 = gabor_filter(0.75,1.5,7*pi/4,I);
% f8 = gabor_filter(0.75,1.5,2*pi,I);
% 
% output = sqrt((f1.^2)+(f2.^2)+(f3.^2)+(f4.^2)+(f5.^2)+(f6.^2)+(f7.^2)+(f8.^2));
 output2 =  f1+f2+f3+f4;
% figure
 imshow(output2,[]);
toc