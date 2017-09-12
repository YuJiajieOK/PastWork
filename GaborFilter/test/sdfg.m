clear all
close all
clc

I = imread('cameraman.jpg');

I = double(I);

mr = 0.5;
mlam = 8;
mb = 1;
mphi = pi/4;
mtheta = pi/4;

mod_re = zeros(size(I,1),size(I,2));
sigma = (1/pi)*sqrt(log(2)/2)*(2^mb+1)*mlam/(2^mb-1); 
for i = 1:size(I,1)
    for j = 1:size(I,2)
        x1 = i*cos(mtheta)+j*sin(mtheta);
        y1 = -i*sin(mtheta)+j*cos(mtheta);
        mod_re(i,j) = exp(   -(x1^2+(mr^2)*(y1^2))/(2*(sigma^2))    )*cos(2*pi*x1/mlam+mphi)*I(i,j);       
    end
end
f = mod_re;
% mu = [0 0]; 
% limit = 20;
% inc = 1;
% [X, Y] = meshgrid(-limit:inc:limit, -limit:inc:limit);
% X_matrix = [X(:) Y(:)];
% 
% u = 1;
% v = 2;
% 
% theta = 45;
% 
% C11 = (u^2)*(cos(theta))^2+(v^2)*(sin(theta))^2;
% C12 = (u^2-v^2)*cos(theta)*sin(theta);
% C21 = (u^2-v^2)*cos(theta)*sin(theta);
% C22 = (u^2)*(sin(theta))^2+(v^2)*(cos(theta))^2;
%  Cov_M = [C11,C12,
%        C21,C22];
%    Z = mvnpdf(X_matrix,mu,Cov_M);
%    Z = reshape(Z,size(-limit:inc:limit,2),size(-limit:inc:limit,2));
%    
%    figure
%    surf(X,Y,Z)
%    figure
%    contour(X,Y,Z)