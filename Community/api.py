from flask import Flask, request, jsonify, make_response
from marshmallow_sqlalchemy import ModelSchema
from flask_sqlalchemy import SQLAlchemy
from marshmallow import fields


## ---------------------------------------------------------------------------------------------------------------------------------------------------
## ---------------------------------------------------------------------------------------------------------------------------------------------------
app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://<username>:<pass>@localhost:3306/<DB>'
db = SQLAlchemy(app)
## ---------------------------------------------------------------------------------------------------------------------------------------------------
## ---------------------------------------------------------------------------------------------------------------------------------------------------


## ----------------------------------------------------------------- Models --------------------------------------------------------------------------
## ---------------------------------------------------------------------------------------------------------------------------------------------------
class Community(db.Model):
 
    __tablename__ = "products"
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(20))
    creation = db.Column(db.String(100))
    lastModified = db.Column(db.String(20))
    description = db.Column(db.String(100))
    location = db.Column(db.String(20))
    photo = db.Column(db.String(100))

    def create(self):
      db.session.add(self)
      db.session.commit()
      return self
  
    # Change the structure.
    def __init__(self, name, creation, lastModified, description, location, photo):
        self.name = name
        self.creation = creation
        self.lastModified = lastModified
        self.description = description
        self.location = location
        self.photo = photo
        
    def __repr__(self):
        return '' % self.id
## ---------------------------------------------------------------------------------------------------------------------------------------------------
## ---------------------------------------------------------------------------------------------------------------------------------------------------
db.create_all()
## ---------------------------------------------------------------------------------------------------------------------------------------------------
## ---------------------------------------------------------------------------------------------------------------------------------------------------
class CommunitySchema(ModelSchema):
    class Meta(ModelSchema.Meta):
        model = Community
        sqla_session = db.session
    id = fields.Number(dump_only=True)
    name = fields.String(required=True)
    creation = fields.String(required=True)
    lastModified = fields.String(required=True)
    description = fields.String(required=True)
    location = fields.String(required=True)
    photo = fields.String(required=True)
## ---------------------------------------------------------------------------------------------------------------------------------------------------
## ---------------------------------------------------------------------------------------------------------------------------------------------------
@app.route('/communities', methods = ['GET'])
def index():
    get_communities = Community.query.all()
    community_schema = CommunitySchema(many=True)
    communities = community_schema.dump(get_communities)
    return make_response(jsonify({"community": communities}))
## ---------------------------------------------------------------------------------------------------------------------------------------------------
## ---------------------------------------------------------------------------------------------------------------------------------------------------
@app.route('/communities/<id>', methods = ['GET'])
def get_product_by_id(id):
    get_community = Community.query.get(id)
    community_schema = CommunitySchema()
    community = community_schema.dump(get_community)
    return make_response(jsonify({"community": community}))
## ---------------------------------------------------------------------------------------------------------------------------------------------------
## ---------------------------------------------------------------------------------------------------------------------------------------------------
@app.route('/community/<id>', methods = ['PUT'])
def update_product_by_id(id):
    data = request.get_json()
    # Necessary to edit the community.
    get_community = Community.query.get(id)
    if data.get('name'):
        get_community.name = data['name']
    # No need to change the creation date.
    if data.get('lastModified'):
        get_community.lastModified = data['lastModified']
    if data.get('description'):
        get_community.description = data['description']    
    if data.get('location'):
        get_community.location = data['location']
    if data.get('photo'):
        get_community.photo = data['photo']
    db.session.add(get_community)
    db.session.commit()
    product_schema = CommunitySchema(only=['id', 'name', 'creation','lastModified','description', 'location', 'photo'])
    product = product_schema.dump(get_community)
    return make_response(jsonify({"product": product}))
## ---------------------------------------------------------------------------------------------------------------------------------------------------
## ---------------------------------------------------------------------------------------------------------------------------------------------------
@app.route('/community/<id>', methods = ['DELETE'])
def delete_product_by_id(id):
    get_community = Community.query.get(id)
    db.session.delete(get_community)
    db.session.commit()
    return make_response("", 204)
## ---------------------------------------------------------------------------------------------------------------------------------------------------
## ---------------------------------------------------------------------------------------------------------------------------------------------------
@app.route('/community', methods = ['POST'])
def create_product():
    data = request.get_json()
    community_schema = CommunitySchema()
    community = community_schema.load(data)
    result = community_schema.dump(community.create())
    return make_response(jsonify({"community": result}), 200)
## ---------------------------------------------------------------------------------------------------------------------------------------------------
## ---------------------------------------------------------------------------------------------------------------------------------------------------
if __name__ == "__main__":
    app.run(debug = True)
## ---------------------------------------------------------------------------------------------------------------------------------------------------
## ---------------------------------------------------------------------------------------------------------------------------------------------------