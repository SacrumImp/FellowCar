using System;
using Newtonsoft.Json;

namespace Abstractions.Authentication
{
    public class SignInOutput
    {
        public SignInOutput()
        {
        }

        [JsonProperty("jwt_token")]
        public string JWTToken { get; set; }

        [JsonProperty("expire_date")]
        public DateTime ExpireDate { get; set; }
    }
}
