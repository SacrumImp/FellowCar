using System;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using Abstractions;
using Abstractions.Authentication;
using Abstractions.Services;
using Entity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Microsoft.IdentityModel.Tokens;

namespace FellowCarAPI.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class AuthorizationController : ControllerBase
    {

        private readonly ILogger<AuthorizationController> _logger;
        private readonly AuthorizationDbContext _context;


        public AuthorizationController(ILogger<AuthorizationController> logger, AuthorizationDbContext context)
        {
            _logger = logger;
            _context = context;
        }


        /// <summary>
        /// Authorization process. Sign in.
        /// </summary>
        /// <param name="params">
        /// <returns></returns>
        [HttpGet("/sign-in")]
        public ActionResult<SignInOutput> SignIn([FromQuery] SignInInput inputParams)
        {
            var user = _context.Users.FirstOrDefault(user => user.Login == inputParams.Login);
            if (user != null && user.Password == inputParams.Password)
            {
                var now = DateTime.UtcNow;
                var expires = now.Add(TimeSpan.FromMinutes(JWTTokenIssuer.LIFETIME));
                var jwt = new JwtSecurityToken(
                        issuer: JWTTokenIssuer.ISSUER,
                        audience: JWTTokenIssuer.AUDIENCE,
                        notBefore: now,
                        expires: expires,
                        signingCredentials: new SigningCredentials(JWTTokenIssuer.GetSymmetricSecurityKey(), SecurityAlgorithms.HmacSha256));
                var encodedJwt = new JwtSecurityTokenHandler().WriteToken(jwt);

                return new SignInOutput()
                {
                    JWTToken = encodedJwt,
                    ExpireDate = expires
                };
            }
            return Unauthorized();

        }

    }
}
