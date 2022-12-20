<?php

    namespace App;
    use Illuminate\Database\Eloquent\Model;

    class Users extends Model
    {
        protected $table = 'users';
        /**
         * The attributes that are mass assignable.
         *
         * @var array
         */
        protected $fillable = [
            'updated_at', 'name', 'lastName', 'email', 'password', 'phone', 'profilePicture', 'passport',
            'driversLicence', 'rating', 'trips'
        ];

    }
