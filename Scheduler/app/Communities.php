<?php

    namespace App;
    use Illuminate\Database\Eloquent\Model;

    class Communities extends Model
    {
        protected $table = 'communities';
        /**
         * The attributes that are mass assignable.
         *
         * @var array
         */
        protected $fillable = [
            'name', 'description', 'location', 'members'
        ];

    }
