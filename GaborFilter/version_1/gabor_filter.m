% close all
% clear all
% clc
% tic

function [f] = gabor_filter(u,v,cita,Image)
%2D Gaussian Distribution 
% u = 6;
% v = 3;
% [X, Y] = meshgrid(-10:1:10, -10:1:10);
% X_matrix = [X(:) Y(:)];
mu = [0 0]; 
limit = 2;
inc = 0.05;
[X, Y] = meshgrid(-limit:inc:limit, -limit:inc:limit);
X_matrix = [X(:) Y(:)];
% cita = 1*pi/4;
C11 = (u^2)*(cos(cita))^2+(v^2)*(sin(cita))^2;
C12 = (u^2-v^2)*cos(cita)*sin(cita);
C21 = (u^2-v^2)*cos(cita)*sin(cita);
C22 = (u^2)*(sin(cita))^2+(v^2)*(cos(cita))^2;
 Cov_M = [C11,C12,
       C21,C22];
   Z = mvnpdf_1(X_matrix,mu,Cov_M);
   Z = reshape(Z,size(-limit:inc:limit,2),size(-limit:inc:limit,2));
% surf(X,Y,Z)
% figure
% contour(X,Y,Z)

%Modulation
% Image = imread('contour-texture.png');
CM = Image;
%figure; imshow(CM);
%title('Orignal');
ReCM = modulation(CM,1);
ImgCM = modulation(CM,2);
% figure
% imshow(ReCM,[]);
% title('mod: real');
% figure
% imshow(ImgCM,[]);
% title('mod: image');

%Convolution
C_ReCM = conv2(ReCM,Z,'same');
C_ImgCM = conv2(ImgCM,Z,'same');
% figure
% imshow(C_ReCM,[]);
% title('convRE');
% figure
% imshow(C_ImgCM,[]);
% title('convImg');


%Demodulation
De_ReCM = Demodulation(C_ReCM,1);
De_ImgCM = Demodulation(C_ImgCM,2);
% figure
% imshow(De_ReCM,[]);
% title('Demod: real');
% figure
% imshow(De_ImgCM,[]);
% title('Demod: image');

%final
f = sqrt(De_ReCM.^2+De_ImgCM.^2);
% figure
% imshow(f,[]);
% title('final');
end

