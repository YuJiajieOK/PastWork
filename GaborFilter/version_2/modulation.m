function [f] = modulation(mI,mtheta,mnum)


% mr = 0.5;
mlam = 3; %Wavelength (?)
% mb = 1;
mphi = pi/4;%Phase offset(s) (?)
mod_re = zeros(size(mI,1),size(mI,2));
mod_imag = zeros(size(mI,1),size(mI,2));

% sigma = (1/pi)*sqrt(log(2)/2)*(2^mb+1)*mlam/(2^mb-1);

if mnum==1;
for i = 1:size(mI,1)
    for j = 1:size(mI,2)
        x1 = i*cos(mtheta)+j*sin(mtheta);
%         y1 = -i*sin(mtheta)+j*cos(mtheta);
        mod_re(i,j) =cos(2*pi*x1/mlam+mphi)*mI(i,j);
%          exp(-(x1^2+(mr^2)*(y1^2))/(2*(sigma^2)))*
    end
end
f = mod_re;
elseif mnum==2;
    for i = 1:size(mI,1)
        for j = 1:size(mI,2)
         x1 = i*cos(mtheta)+j*sin(mtheta);
%          y1 = -i*sin(mtheta)+j*cos(mtheta);
         mod_imag(i,j) = sin(2*pi*x1/mlam+mphi)*mI(i,j);
%          exp(-(x1^2+(mr^2)*(y1^2))/(2*(sigma^2)))*
        end
    end
f = mod_imag;
else
    disp('Parameter error.');
end
end
