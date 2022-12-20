<?php

    namespace App;
    use Illuminate\Database\Eloquent\Model;

    class Trips extends Model
    {
        protected $table = 'trips';
        /**
         * The attributes that are mass assignable.
         *
         * @var array
         */
        protected $fillable = [
            'id', 'origin', 'destiny', 'hour'
        ];

    }
