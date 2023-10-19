// License: LGPL-3.0 License (c) security-code-scan
using System.Web.Mvc;

class AuthorizationBypassUnsafe : Controller
{
  public ActionResult Login()
  {
    return null;
  }

  [Authorize]
  public ActionResult Logout()
  {
    return null;
  }
}

[Authorize]
class AuthorizationBypassSafe : Controller
{
  public ActionResult Login()
  {
    return null;
  }

  [Authorize]
  public ActionResult Logout()
  {
    return null;
  }
}