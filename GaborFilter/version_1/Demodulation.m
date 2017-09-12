
%This funtion is used to demodulation the image.The paremeter CameraMann is
%an input image the Complex with variable 1 represents the Real part
%variable 2 is representing imaginary part. Ohterwise display error.
function [f] = Demodulation(CameraMan,Complex)

u0 = -1/5;
v0 = -1/5;

r = size(CameraMan,1);
c = size(CameraMan,2);

Re = zeros(r,c);
Im = zeros(r,c);
f = zeros(r,c);
%d = zeros(r,c);

if Complex == 1;
for i = 1:size(CameraMan,1)
    for j = 1:size(CameraMan,2)
        Re(i,j) = cos(2*pi*(u0*i+v0*j));       
        f(i,j) = CameraMan(i,j)*Re(i,j);
    end
end
elseif Complex == 2;
    for i = 1:size(CameraMan,1)
        for j = 1:size(CameraMan,2)
           Im(i,j) = sin(2*pi*(u0*i+v0*j));
           f(i,j) = CameraMan(i,j)*Im(i,j);
        end
    end
else
    disp('Parameter error');
end

% F0 = sqrt(u0^2+v0^2);
% omega0 = 1/tan(v0/u0);
% 
% for i = 1:size(d,1)
%     for j = 1:size(d,2)
%         d(i,j) = f(i,j)*exp(2*pi*F0*(i*cos(omega0)+j*sin(omega0)));
%     end
% end
        
    