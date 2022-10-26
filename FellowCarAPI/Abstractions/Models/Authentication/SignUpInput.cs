using System;
using System.ComponentModel.DataAnnotations;
using Newtonsoft.Json;

namespace Abstractions.Models.Authentication
{
    public class SignUpInput
    {

        [Required, JsonProperty("login")]
        public string Login { get; set; }

        [Required, JsonProperty("password")]
        public string Password { get; set; }

        [Required, JsonProperty("email")]
        public string Email { get; set; }

        public SignUpInput()
        {
        }
    }
}
