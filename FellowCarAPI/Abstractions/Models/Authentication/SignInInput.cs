using System;
using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;

namespace Abstractions.Authentication
{
    public class SignInInput
    {
        [Required, FromQuery(Name = "login")]
        public string Login { get; set; }

        [Required, FromQuery(Name = "password")]
        public string Password { get; set; }
    }
}
