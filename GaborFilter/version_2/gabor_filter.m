function [f] = gabor_filter(I,theta)

I = double(I);

% guassian distribution
r = 0.5;%Aspect ratio (?)
b = 1;%Bandwidth (b)
lam = 3;%Wavelength (?)
%sigma = (1/pi)*sqrt(log(2)/2)*(2^b+1)*lam/(2^b-1);
sigma = (lam/pi)*sqrt(log(2)/2)*(2^b+1)/(2^b-1);
% mu = [0 0]; 
limit = 20;
inc = 1;
[X, Y] = meshgrid(-limit:inc:limit, -limit:inc:limit);
X_matrix = [X(:) Y(:)];
 Z = [];
        
for i = 1:size(X_matrix,1)      
 x1 = X_matrix(i,1)*cos(theta)+X_matrix(i,2)*sin(theta);
 y1 = - X_matrix(i,1)*sin(theta)+X_matrix(i,2)*cos(theta);
Z(i,1) = exp(-(x1^2+(r^2)*(y1^2))/(2*sigma^2));
end
% u = 3;
% v = 1;
% 
% C11 = (u^2)*(cos(theta))^2+(v^2)*(sin(theta))^2;
% C12 = (u^2-v^2)*cos(theta)*sin(theta);
% C21 = (u^2-v^2)*cos(theta)*sin(theta);
% C22 = (u^2)*(sin(theta))^2+(v^2)*(cos(theta))^2;
%  Cov_M = [C11,C12,
%        C21,C22];
%    Z = mvnpdf(X_matrix,mu,Cov_M);

   Z = reshape(Z,size(-limit:inc:limit,2),size(-limit:inc:limit,2));
   
%    figure
%    surf(X,Y,Z)
%    figure
%    contour(X,Y,Z)
   
   %modulation
   mod_re = modulation(I,theta,1);
   mod_imag = modulation(I,theta,2);
   
%    figure
%    imshow(mod_re,[]);
%    figure
%    imshow(mod_imag,[]);
   
   %convolution
   con_mod_re = conv2(mod_re,Z,'same');
   con_mod_imag = conv2(mod_imag,Z,'same');
   
%    figure
%    imshow(con_mod_re,[]);
%    figure
%    imshow(con_mod_imag,[]);
   
   %demodulation
   demod_re = demodulation(con_mod_re,theta,1);
   demod_imag = demodulation(con_mod_imag,theta,2);
%    figure
%    imshow(demod_re,[]);
%    figure
%    imshow(demod_imag,[]);
  
   f = sqrt(demod_re.^2+demod_imag.^2);
end
   