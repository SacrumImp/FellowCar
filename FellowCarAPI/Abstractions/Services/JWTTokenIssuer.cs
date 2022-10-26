using System;
using System.Text;
using Microsoft.IdentityModel.Tokens;

namespace Abstractions.Services
{
    public class JWTTokenIssuer
    {
        public const string ISSUER = "FellowCarAuthServer"; 
        public const string AUDIENCE = "FellowCarPhoneApp"; 
        const string KEY = "efwefwe3023r92jg848v4nvorveorvervp4";   
        public const int LIFETIME = 480; // mins

        public JWTTokenIssuer()
        {
        }

        public static SymmetricSecurityKey GetSymmetricSecurityKey()
        {
            return new SymmetricSecurityKey(Encoding.ASCII.GetBytes(KEY));
        }
    }
}
