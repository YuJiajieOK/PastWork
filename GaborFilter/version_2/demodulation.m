function [f] = demodulation(dI,dtheta,dnum)

% dr = 0.5;
dlam = 3; %Wavelength (?)
% db = 1;
dphi = pi/4;%Phase offset(s) (?)
demod_re = zeros(size(dI,1),size(dI,2));
demod_imag = zeros(size(dI,1),size(dI,2));

% sigma = (1/pi)*sqrt(log(2)/2)*(2^db+1)*dlam/(2^db-1);

if dnum==1;
for i = 1:size(dI,1)
    for j = 1:size(dI,2)
        x1 = i*cos(dtheta)+j*sin(dtheta);
        x1 = -x1;
%         y1 = -i*sin(dtheta)+j*cos(dtheta);
%         y1 = -y1;
        demod_re(i,j) = cos(2*pi*x1/dlam+dphi)*dI(i,j);
%         exp(-(x1^2+(dr^2)*(y1^2))/(2*(sigma^2)))*
    end
end
        f = demod_re;
elseif dnum==2;
    for i = 1:size(dI,1)
        for j = 1:size(dI,2)
         x1 = i*cos(dtheta)+j*sin(dtheta);
         x1 = -x1;
%          y1 = -i*sin(dtheta)+j*cos(dtheta);
%          y1 = -y1;
         demod_imag(i,j) =  sin(2*pi*x1/dlam+dphi)*dI(i,j);
%          exp(-(x1^2+(dr^2)*(y1^2))/(2*(sigma^2)))*
        
        end
    end
     f = demod_imag;
else
    disp('Parameter error.');
end
end
