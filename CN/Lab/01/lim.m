x = linspace(5e7, 10^8)

f = @(x) x.*(sqrt(x.^2 + 1) - x)

g = @(x) x./(sqrt(x.^2 + 1) + x)
