using System;
using Entity.Models;
using Microsoft.EntityFrameworkCore;

namespace Entity
{
    public class AuthorizationDbContext : DbContext
    {

        public AuthorizationDbContext(DbContextOptions options): base(options)
        { }

        public DbSet<Users> Users { get; set; }

    }
}
